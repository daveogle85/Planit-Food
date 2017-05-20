"use strict";
const MainModule_1 = require("./modules/MainModule");
const platform_browser_dynamic_1 = require("@angular/platform-browser-dynamic");
const core_1 = require("@angular/core");
core_1.enableProdMode();
const platform = platform_browser_dynamic_1.platformBrowserDynamic();
platform.bootstrapModule(MainModule_1.AppModule);
//# sourceMappingURL=main.js.map