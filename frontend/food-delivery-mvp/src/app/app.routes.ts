import { Routes } from '@angular/router';
import { RestaurantListingComponent } from './restaurant-listing/component/restaurant-listing.component';

export const routes: Routes = [
   
    { path: '', redirectTo: 'restaurants', pathMatch: 'full' },
    { path: 'restaurants', component: RestaurantListingComponent },
    {
        path: 'food-catalogue/:id',
        loadChildren: () =>
            import('./food-catalogue/food-catalogue.routes').then((m) => m.foodCatalogueRoutes)
    },
    {
        path: 'orderSummary',
        loadChildren: () =>
            import('./order-service/order-service.routes').then((m) => m.orderServiceRoutes)
    },
    { path: '**', redirectTo: 'restaurants' }
    
];
