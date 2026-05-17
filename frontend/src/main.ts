import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config'; // <-- Sprawdź, czy importujesz ten plik!

// Drugim argumentem funkcji bootstrapApplication MUSI być appConfig
bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
