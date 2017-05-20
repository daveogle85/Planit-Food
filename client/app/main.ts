import { AppModule } from "./modules/MainModule";
import { platformBrowserDynamic } from "@angular/platform-browser-dynamic";
import { enableProdMode } from "@angular/core";

enableProdMode();
const platform = platformBrowserDynamic();
platform.bootstrapModule(AppModule);
