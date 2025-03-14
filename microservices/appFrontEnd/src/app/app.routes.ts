import { Routes } from '@angular/router';
import { AddProductComponent } from './components/add-product/add-product.component';
import { ListProductComponent } from './components/list-product/list-product.component';
import { AccueilComponent } from './components/accueil/accueil.component';
import { ListCommandComponent } from './list-command/list-command.component';
import { CreateCommandComponent } from './create-command/create-command.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'accueil',
        pathMatch: 'full'
    },
    {
        path: 'accueil',
        component: AccueilComponent
    },
    {
        path: 'add-product',
        component: AddProductComponent
    },
    {
        path: 'list-product',
        component: ListProductComponent
    },
    {
        path: 'list-command',
        component: ListCommandComponent
    },
    {
        path: 'create-command',
        component: CreateCommandComponent
    }
];
