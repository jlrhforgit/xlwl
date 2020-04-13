/*
 * [js-sha1]{@link https://github.com/emn178/js-sha1}
 *
 * @version 0.6.0
 * @author Chen, Yi-Cyuan [emn178@gmail.com]
 * @copyright Chen, Yi-Cyuan 2014-2017
 * @license MIT
 
jslint bitwise: true 
(function() {
	'use strict';

	var root = typeof window === 'object' ? window : {};
	var NODE_JS = !root.JS_SHA1_NO_NODE_JS && typeof process === 'object'
			&& process.versions && process.versions.node;
	if (NODE_JS) {
		root = global;
	}
	var COMMON_JS = !root.JS_SHA1_NO_COMMON_JS && typeof module === 'object'
			&& module.exports;
	var AMD = typeof define === 'function' && define.amd;
	var HEX_CHARS = '0123456789abcdef'.split('');
	var EXTRA = [ -2147483648, 8388608, 32768, 128 ];
	var SHIFT = [ 24, 16, 8, 0 ];
	var OUTPUT_TYPES = [ 'hex', 'array', 'digest', 'arrayBuffer' ];

	var blocks = [];

	var createOutputMethod = function(outputType) {
		return function(message) {
			return new Sha1(true).update(message)[outputType]();
		};
	};

	var createMethod = function() {
		var method = createOutputMethod('hex');
		if (NODE_JS) {
			method = nodeWrap(method);
		}
		method.create = function() {
			return new Sha1();
		};
		method.update = function(message) {
			return method.create().update(message);
		};
		for (var i = 0; i < OUTPUT_TYPES.length; ++i) {
			var type = OUTPUT_TYPES[i];
			method[type] = createOutputMethod(type);
		}
		return method;
	};

	var nodeWrap = function(method) {
		var crypto = eval("require('crypto')");
		var Buffer = eval("require('buffer').Buffer");
		var nodeMethod = function(message) {
			if (typeof message === 'string') {
				return crypto.createHash('sha1').update(message, 'utf8')
						.digest('hex');
			} else if (message.constructor === ArrayBuffer) {
				message = new Uint8Array(message);
			} else if (message.length === undefined) {
				return method(message);
			}
			return crypto.createHash('sha1').update(new Buffer(message))
					.digest('hex');
		};
		return nodeMethod;
	};

	function Sha1(sharedMemory) {
		if (sharedMemory) {
			blocks[0] = blocks[16] = blocks[1] = blocks[2] = blocks[3] = blocks[4] = blocks[5] = blocks[6] = blocks[7] = blocks[8] = blocks[9] = blocks[10] = blocks[11] = blocks[12] = blocks[13] = blocks[14] = blocks[15] = 0;
			this.blocks = blocks;
		} else {
			this.blocks = [ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ];
		}

		this.h0 = 0x67452301;
		this.h1 = 0xEFCDAB89;
		this.h2 = 0x98BADCFE;
		this.h3 = 0x10325476;
		this.h4 = 0xC3D2E1F0;

		this.block = this.start = this.bytes = this.hBytes = 0;
		this.finalized = this.hashed = false;
		this.first = true;
	}

	Sha1.prototype.update = function(message) {
		if (this.finalized) {
			return;
		}
		var notString = typeof (message) !== 'string';
		if (notString && message.constructor === root.ArrayBuffer) {
			message = new Uint8Array(message);
		}
		var code, index = 0, i, length = message.length || 0, blocks = this.blocks;

		while (index < length) {
			if (this.hashed) {
				this.hashed = false;
				blocks[0] = this.block;
				blocks[16] = blocks[1] = blocks[2] = blocks[3] = blocks[4] = blocks[5] = blocks[6] = blocks[7] = blocks[8] = blocks[9] = blocks[10] = blocks[11] = blocks[12] = blocks[13] = blocks[14] = blocks[15] = 0;
			}

			if (notString) {
				for (i = this.start; index < length && i < 64; ++index) {
					blocks[i >> 2] |= message[index] << SHIFT[i++ & 3];
				}
			} else {
				for (i = this.start; index < length && i < 64; ++index) {
					code = message.charCodeAt(index);
					if (code < 0x80) {
						blocks[i >> 2] |= code << SHIFT[i++ & 3];
					} else if (code < 0x800) {
						blocks[i >> 2] |= (0xc0 | (code >> 6)) << SHIFT[i++ & 3];
						blocks[i >> 2] |= (0x80 | (code & 0x3f)) << SHIFT[i++ & 3];
					} else if (code < 0xd800 || code >= 0xe000) {
						blocks[i >> 2] |= (0xe0 | (code >> 12)) << SHIFT[i++ & 3];
						blocks[i >> 2] |= (0x80 | ((code >> 6) & 0x3f)) << SHIFT[i++ & 3];
						blocks[i >> 2] |= (0x80 | (code & 0x3f)) << SHIFT[i++ & 3];
					} else {
						code = 0x10000 + (((code & 0x3ff) << 10) | (message
								.charCodeAt(++index) & 0x3ff));
						blocks[i >> 2] |= (0xf0 | (code >> 18)) << SHIFT[i++ & 3];
						blocks[i >> 2] |= (0x80 | ((code >> 12) & 0x3f)) << SHIFT[i++ & 3];
						blocks[i >> 2] |= (0x80 | ((code >> 6) & 0x3f)) << SHIFT[i++ & 3];
						blocks[i >> 2] |= (0x80 | (code & 0x3f)) << SHIFT[i++ & 3];
					}
				}
			}

			this.lastByteIndex = i;
			this.bytes += i - this.start;
			if (i >= 64) {
				this.block = blocks[16];
				this.start = i - 64;
				this.hash();
				this.hashed = true;
			} else {
				this.start = i;
			}
		}
		if (this.bytes > 4294967295) {
			this.hBytes += this.bytes / 4294967296 << 0;
			this.bytes = this.bytes % 4294967296;
		}
		return this;
	};

	Sha1.prototype.finalize = function() {
		if (this.finalized) {
			return;
		}
		this.finalized = true;
		var blocks = this.blocks, i = this.lastByteIndex;
		blocks[16] = this.block;
		blocks[i >> 2] |= EXTRA[i & 3];
		this.block = blocks[16];
		if (i >= 56) {
			if (!this.hashed) {
				this.hash();
			}
			blocks[0] = this.block;
			blocks[16] = blocks[1] = blocks[2] = blocks[3] = blocks[4] = blocks[5] = blocks[6] = blocks[7] = blocks[8] = blocks[9] = blocks[10] = blocks[11] = blocks[12] = blocks[13] = blocks[14] = blocks[15] = 0;
		}
		blocks[14] = this.hBytes << 3 | this.bytes >>> 29;
		blocks[15] = this.bytes << 3;
		this.hash();
	};

	Sha1.prototype.hash = function() {
		var a = this.h0, b = this.h1, c = this.h2, d = this.h3, e = this.h4;
		var f, j, t, blocks = this.blocks;

		for (j = 16; j < 80; ++j) {
			t = blocks[j - 3] ^ blocks[j - 8] ^ blocks[j - 14] ^ blocks[j - 16];
			blocks[j] = (t << 1) | (t >>> 31);
		}

		for (j = 0; j < 20; j += 5) {
			f = (b & c) | ((~b) & d);
			t = (a << 5) | (a >>> 27);
			e = t + f + e + 1518500249 + blocks[j] << 0;
			b = (b << 30) | (b >>> 2);

			f = (a & b) | ((~a) & c);
			t = (e << 5) | (e >>> 27);
			d = t + f + d + 1518500249 + blocks[j + 1] << 0;
			a = (a << 30) | (a >>> 2);

			f = (e & a) | ((~e) & b);
			t = (d << 5) | (d >>> 27);
			c = t + f + c + 1518500249 + blocks[j + 2] << 0;
			e = (e << 30) | (e >>> 2);

			f = (d & e) | ((~d) & a);
			t = (c << 5) | (c >>> 27);
			b = t + f + b + 1518500249 + blocks[j + 3] << 0;
			d = (d << 30) | (d >>> 2);

			f = (c & d) | ((~c) & e);
			t = (b << 5) | (b >>> 27);
			a = t + f + a + 1518500249 + blocks[j + 4] << 0;
			c = (c << 30) | (c >>> 2);
		}

		for (; j < 40; j += 5) {
			f = b ^ c ^ d;
			t = (a << 5) | (a >>> 27);
			e = t + f + e + 1859775393 + blocks[j] << 0;
			b = (b << 30) | (b >>> 2);

			f = a ^ b ^ c;
			t = (e << 5) | (e >>> 27);
			d = t + f + d + 1859775393 + blocks[j + 1] << 0;
			a = (a << 30) | (a >>> 2);

			f = e ^ a ^ b;
			t = (d << 5) | (d >>> 27);
			c = t + f + c + 1859775393 + blocks[j + 2] << 0;
			e = (e << 30) | (e >>> 2);

			f = d ^ e ^ a;
			t = (c << 5) | (c >>> 27);
			b = t + f + b + 1859775393 + blocks[j + 3] << 0;
			d = (d << 30) | (d >>> 2);

			f = c ^ d ^ e;
			t = (b << 5) | (b >>> 27);
			a = t + f + a + 1859775393 + blocks[j + 4] << 0;
			c = (c << 30) | (c >>> 2);
		}

		for (; j < 60; j += 5) {
			f = (b & c) | (b & d) | (c & d);
			t = (a << 5) | (a >>> 27);
			e = t + f + e - 1894007588 + blocks[j] << 0;
			b = (b << 30) | (b >>> 2);

			f = (a & b) | (a & c) | (b & c);
			t = (e << 5) | (e >>> 27);
			d = t + f + d - 1894007588 + blocks[j + 1] << 0;
			a = (a << 30) | (a >>> 2);

			f = (e & a) | (e & b) | (a & b);
			t = (d << 5) | (d >>> 27);
			c = t + f + c - 1894007588 + blocks[j + 2] << 0;
			e = (e << 30) | (e >>> 2);

			f = (d & e) | (d & a) | (e & a);
			t = (c << 5) | (c >>> 27);
			b = t + f + b - 1894007588 + blocks[j + 3] << 0;
			d = (d << 30) | (d >>> 2);

			f = (c & d) | (c & e) | (d & e);
			t = (b << 5) | (b >>> 27);
			a = t + f + a - 1894007588 + blocks[j + 4] << 0;
			c = (c << 30) | (c >>> 2);
		}

		for (; j < 80; j += 5) {
			f = b ^ c ^ d;
			t = (a << 5) | (a >>> 27);
			e = t + f + e - 899497514 + blocks[j] << 0;
			b = (b << 30) | (b >>> 2);

			f = a ^ b ^ c;
			t = (e << 5) | (e >>> 27);
			d = t + f + d - 899497514 + blocks[j + 1] << 0;
			a = (a << 30) | (a >>> 2);

			f = e ^ a ^ b;
			t = (d << 5) | (d >>> 27);
			c = t + f + c - 899497514 + blocks[j + 2] << 0;
			e = (e << 30) | (e >>> 2);

			f = d ^ e ^ a;
			t = (c << 5) | (c >>> 27);
			b = t + f + b - 899497514 + blocks[j + 3] << 0;
			d = (d << 30) | (d >>> 2);

			f = c ^ d ^ e;
			t = (b << 5) | (b >>> 27);
			a = t + f + a - 899497514 + blocks[j + 4] << 0;
			c = (c << 30) | (c >>> 2);
		}

		this.h0 = this.h0 + a << 0;
		this.h1 = this.h1 + b << 0;
		this.h2 = this.h2 + c << 0;
		this.h3 = this.h3 + d << 0;
		this.h4 = this.h4 + e << 0;
	};

	Sha1.prototype.hex = function() {
		this.finalize();

		var h0 = this.h0, h1 = this.h1, h2 = this.h2, h3 = this.h3, h4 = this.h4;

		return HEX_CHARS[(h0 >> 28) & 0x0F] + HEX_CHARS[(h0 >> 24) & 0x0F]
				+ HEX_CHARS[(h0 >> 20) & 0x0F] + HEX_CHARS[(h0 >> 16) & 0x0F]
				+ HEX_CHARS[(h0 >> 12) & 0x0F] + HEX_CHARS[(h0 >> 8) & 0x0F]
				+ HEX_CHARS[(h0 >> 4) & 0x0F] + HEX_CHARS[h0 & 0x0F]
				+ HEX_CHARS[(h1 >> 28) & 0x0F] + HEX_CHARS[(h1 >> 24) & 0x0F]
				+ HEX_CHARS[(h1 >> 20) & 0x0F] + HEX_CHARS[(h1 >> 16) & 0x0F]
				+ HEX_CHARS[(h1 >> 12) & 0x0F] + HEX_CHARS[(h1 >> 8) & 0x0F]
				+ HEX_CHARS[(h1 >> 4) & 0x0F] + HEX_CHARS[h1 & 0x0F]
				+ HEX_CHARS[(h2 >> 28) & 0x0F] + HEX_CHARS[(h2 >> 24) & 0x0F]
				+ HEX_CHARS[(h2 >> 20) & 0x0F] + HEX_CHARS[(h2 >> 16) & 0x0F]
				+ HEX_CHARS[(h2 >> 12) & 0x0F] + HEX_CHARS[(h2 >> 8) & 0x0F]
				+ HEX_CHARS[(h2 >> 4) & 0x0F] + HEX_CHARS[h2 & 0x0F]
				+ HEX_CHARS[(h3 >> 28) & 0x0F] + HEX_CHARS[(h3 >> 24) & 0x0F]
				+ HEX_CHARS[(h3 >> 20) & 0x0F] + HEX_CHARS[(h3 >> 16) & 0x0F]
				+ HEX_CHARS[(h3 >> 12) & 0x0F] + HEX_CHARS[(h3 >> 8) & 0x0F]
				+ HEX_CHARS[(h3 >> 4) & 0x0F] + HEX_CHARS[h3 & 0x0F]
				+ HEX_CHARS[(h4 >> 28) & 0x0F] + HEX_CHARS[(h4 >> 24) & 0x0F]
				+ HEX_CHARS[(h4 >> 20) & 0x0F] + HEX_CHARS[(h4 >> 16) & 0x0F]
				+ HEX_CHARS[(h4 >> 12) & 0x0F] + HEX_CHARS[(h4 >> 8) & 0x0F]
				+ HEX_CHARS[(h4 >> 4) & 0x0F] + HEX_CHARS[h4 & 0x0F];
	};

	Sha1.prototype.toString = Sha1.prototype.hex;

	Sha1.prototype.digest = function() {
		this.finalize();

		var h0 = this.h0, h1 = this.h1, h2 = this.h2, h3 = this.h3, h4 = this.h4;

		return [ (h0 >> 24) & 0xFF, (h0 >> 16) & 0xFF, (h0 >> 8) & 0xFF,
				h0 & 0xFF, (h1 >> 24) & 0xFF, (h1 >> 16) & 0xFF,
				(h1 >> 8) & 0xFF, h1 & 0xFF, (h2 >> 24) & 0xFF,
				(h2 >> 16) & 0xFF, (h2 >> 8) & 0xFF, h2 & 0xFF,
				(h3 >> 24) & 0xFF, (h3 >> 16) & 0xFF, (h3 >> 8) & 0xFF,
				h3 & 0xFF, (h4 >> 24) & 0xFF, (h4 >> 16) & 0xFF,
				(h4 >> 8) & 0xFF, h4 & 0xFF ];
	};

	Sha1.prototype.array = Sha1.prototype.digest;

	Sha1.prototype.arrayBuffer = function() {
		this.finalize();

		var buffer = new ArrayBuffer(20);
		var dataView = new DataView(buffer);
		dataView.setUint32(0, this.h0);
		dataView.setUint32(4, this.h1);
		dataView.setUint32(8, this.h2);
		dataView.setUint32(12, this.h3);
		dataView.setUint32(16, this.h4);
		return buffer;
	};

	var exports = createMethod();

	if (COMMON_JS) {
		module.exports = exports;
	} else {
		root.sha1 = exports;
		if (AMD) {
			define(function() {
				return exports;
			});
		}
	}
})();
 */

/*
 * A JavaScript implementation of the Secure Hash Algorithm, SHA-1, as defined
 * in FIPS PUB 180-1
 * Version 2.1-BETA Copyright Paul Johnston 2000 - 2002.
 * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
 * Distributed under the BSD License
 * See http://pajhome.org.uk/crypt/md5 for details.
 */
/*
 * Configurable variables. You may need to tweak these to be compatible with
 * the server-side, but the defaults work in most cases.
 */
var hexcase = 0; /* hex output format. 0 - lowercase; 1 - uppercase */
var b64pad = ""; /* base-64 pad character. "=" for strict RFC compliance */
var chrsz = 8; /* bits per input character. 8 - ASCII; 16 - Unicode */
/*
 * These are the functions you'll usually want to call They take string
 * arguments and return either hex or base-64 encoded strings
 */
function hex_sha1(s) {
	return binb2hex(core_sha1(str2binb(s), s.length * chrsz));
}
function b64_sha1(s) {
	return binb2b64(core_sha1(str2binb(s), s.length * chrsz));
}
function str_sha1(s) {
	return binb2str(core_sha1(str2binb(s), s.length * chrsz));
}
function hex_hmac_sha1(key, data) {
	return binb2hex(core_hmac_sha1(key, data));
}
function b64_hmac_sha1(key, data) {
	return binb2b64(core_hmac_sha1(key, data));
}
function str_hmac_sha1(key, data) {
	return binb2str(core_hmac_sha1(key, data));
}
/*
 * Perform a simple self-test to see if the VM is working
 */
function sha1_vm_test() {
	return hex_sha1("abc") == "a9993e364706816aba3e25717850c26c9cd0d89d";
}
/*
 * Calculate the SHA-1 of an array of big-endian words, and a bit length
 */
function core_sha1(x, len) {
	/* append padding */
	x[len >> 5] |= 0x80 << (24 - len % 32);
	x[((len + 64 >> 9) << 4) + 15] = len;
	var w = Array(80);
	var a = 1732584193;
	var b = -271733879;
	var c = -1732584194;
	var d = 271733878;
	var e = -1009589776;
	for (var i = 0; i < x.length; i += 16) {
		var olda = a;
		var oldb = b;
		var oldc = c;
		var oldd = d;
		var olde = e;
		for (var j = 0; j < 80; j++) {
			if (j < 16)
				w[j] = x[i + j];
			else
				w[j] = rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
			var t = safe_add(safe_add(rol(a, 5), sha1_ft(j, b, c, d)),
					safe_add(safe_add(e, w[j]), sha1_kt(j)));
			e = d;
			d = c;
			c = rol(b, 30);
			b = a;
			a = t;
		}
		a = safe_add(a, olda);
		b = safe_add(b, oldb);
		c = safe_add(c, oldc);
		d = safe_add(d, oldd);
		e = safe_add(e, olde);
	}
	return Array(a, b, c, d, e);
}
/*
 * Perform the appropriate triplet combination function for the current
 * iteration
 */
function sha1_ft(t, b, c, d) {
	if (t < 20)
		return (b & c) | ((~b) & d);
	if (t < 40)
		return b ^ c ^ d;
	if (t < 60)
		return (b & c) | (b & d) | (c & d);
	return b ^ c ^ d;
}
/*
 * Determine the appropriate additive constant for the current iteration
 */
function sha1_kt(t) {
	return (t < 20) ? 1518500249 : (t < 40) ? 1859775393
			: (t < 60) ? -1894007588 : -899497514;
}
/*
 * Calculate the HMAC-SHA1 of a key and some data
 */
function core_hmac_sha1(key, data) {
	var bkey = str2binb(key);
	if (bkey.length > 16)
		bkey = core_sha1(bkey, key.length * chrsz);
	var ipad = Array(16), opad = Array(16);
	for (var i = 0; i < 16; i++) {
		ipad[i] = bkey[i] ^ 0x36363636;
		opad[i] = bkey[i] ^ 0x5C5C5C5C;
	}
	var hash = core_sha1(ipad.concat(str2binb(data)), 512 + data.length * chrsz);
	return core_sha1(opad.concat(hash), 512 + 160);
}
/*
 * Add integers, wrapping at 2^32. This uses 16-bit operations internally to
 * work around bugs in some JS interpreters.
 */
function safe_add(x, y) {
	var lsw = (x & 0xFFFF) + (y & 0xFFFF);
	var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
	return (msw << 16) | (lsw & 0xFFFF);
}
/*
 * Bitwise rotate a 32-bit number to the left.
 */
function rol(num, cnt) {
	return (num << cnt) | (num >>> (32 - cnt));
}
/*
 * Convert an 8-bit or 16-bit string to an array of big-endian words In 8-bit
 * function, characters >255 have their hi-byte silently ignored.
 */
function str2binb(str) {
	var bin = Array();
	var mask = (1 << chrsz) - 1;
	for (var i = 0; i < str.length * chrsz; i += chrsz)
		bin[i >> 5] |= (str.charCodeAt(i / chrsz) & mask) << (24 - i % 32);
	return bin;
}
/*
 * Convert an array of big-endian words to a string
 */
function binb2str(bin) {
	var str = "";
	var mask = (1 << chrsz) - 1;
	for (var i = 0; i < bin.length * 32; i += chrsz)
		str += String.fromCharCode((bin[i >> 5] >>> (24 - i % 32)) & mask);
	return str;
}
/*
 * Convert an array of big-endian words to a hex string.
 */
function binb2hex(binarray) {
	var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
	var str = "";
	for (var i = 0; i < binarray.length * 4; i++) {
		str += hex_tab
				.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8 + 4)) & 0xF)
				+ hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8)) & 0xF);
	}
	return str;
}
/*
 * Convert an array of big-endian words to a base-64 string
 */
function binb2b64(binarray) {
	var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	var str = "";
	for (var i = 0; i < binarray.length * 4; i += 3) {
		var triplet = (((binarray[i >> 2] >> 8 * (3 - i % 4)) & 0xFF) << 16)
				| (((binarray[i + 1 >> 2] >> 8 * (3 - (i + 1) % 4)) & 0xFF) << 8)
				| ((binarray[i + 2 >> 2] >> 8 * (3 - (i + 2) % 4)) & 0xFF);
		for (var j = 0; j < 4; j++) {
			if (i * 8 + j * 6 > binarray.length * 32)
				str += b64pad;
			else
				str += tab.charAt((triplet >> 6 * (3 - j)) & 0x3F);
		}
	}
	return str;
}