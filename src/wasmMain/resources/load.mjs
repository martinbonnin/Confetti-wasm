import { instantiate } from './Confetti.uninstantiated.mjs';

await wasmSetup;

await instantiate({ skia: Module['asm'] });
