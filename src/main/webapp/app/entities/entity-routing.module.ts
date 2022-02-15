import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'patient-info',
        data: { pageTitle: 'icmrDailyCountUpdation2App.patientInfo.home.title' },
        loadChildren: () => import('./patient-info/patient-info.module').then(m => m.PatientInfoModule),
      },
      {
        path: 'state',
        data: { pageTitle: 'icmrDailyCountUpdation2App.state.home.title' },
        loadChildren: () => import('./state/state.module').then(m => m.StateModule),
      },
      {
        path: 'division',
        data: { pageTitle: 'icmrDailyCountUpdation2App.division.home.title' },
        loadChildren: () => import('./division/division.module').then(m => m.DivisionModule),
      },
      {
        path: 'district',
        data: { pageTitle: 'icmrDailyCountUpdation2App.district.home.title' },
        loadChildren: () => import('./district/district.module').then(m => m.DistrictModule),
      },
      {
        path: 'taluka',
        data: { pageTitle: 'icmrDailyCountUpdation2App.taluka.home.title' },
        loadChildren: () => import('./taluka/taluka.module').then(m => m.TalukaModule),
      },
      {
        path: 'city',
        data: { pageTitle: 'icmrDailyCountUpdation2App.city.home.title' },
        loadChildren: () => import('./city/city.module').then(m => m.CityModule),
      },
      {
        path: 'municipal-corp',
        data: { pageTitle: 'icmrDailyCountUpdation2App.municipalCorp.home.title' },
        loadChildren: () => import('./municipal-corp/municipal-corp.module').then(m => m.MunicipalCorpModule),
      },
      {
        path: 'user-access',
        data: { pageTitle: 'icmrDailyCountUpdation2App.userAccess.home.title' },
        loadChildren: () => import('./user-access/user-access.module').then(m => m.UserAccessModule),
      },
      {
        path: 'security-user',
        data: { pageTitle: 'icmrDailyCountUpdation2App.securityUser.home.title' },
        loadChildren: () => import('./security-user/security-user.module').then(m => m.SecurityUserModule),
      },
      {
        path: 'security-role',
        data: { pageTitle: 'icmrDailyCountUpdation2App.securityRole.home.title' },
        loadChildren: () => import('./security-role/security-role.module').then(m => m.SecurityRoleModule),
      },
      {
        path: 'security-permission',
        data: { pageTitle: 'icmrDailyCountUpdation2App.securityPermission.home.title' },
        loadChildren: () => import('./security-permission/security-permission.module').then(m => m.SecurityPermissionModule),
      },
      {
        path: 'hospital-type',
        data: { pageTitle: 'icmrDailyCountUpdation2App.hospitalType.home.title' },
        loadChildren: () => import('./hospital-type/hospital-type.module').then(m => m.HospitalTypeModule),
      },
      {
        path: 'bed-type',
        data: { pageTitle: 'icmrDailyCountUpdation2App.bedType.home.title' },
        loadChildren: () => import('./bed-type/bed-type.module').then(m => m.BedTypeModule),
      },
      {
        path: 'inventory-type',
        data: { pageTitle: 'icmrDailyCountUpdation2App.inventoryType.home.title' },
        loadChildren: () => import('./inventory-type/inventory-type.module').then(m => m.InventoryTypeModule),
      },
      {
        path: 'inventory-master',
        data: { pageTitle: 'icmrDailyCountUpdation2App.inventoryMaster.home.title' },
        loadChildren: () => import('./inventory-master/inventory-master.module').then(m => m.InventoryMasterModule),
      },
      {
        path: 'hospital',
        data: { pageTitle: 'icmrDailyCountUpdation2App.hospital.home.title' },
        loadChildren: () => import('./hospital/hospital.module').then(m => m.HospitalModule),
      },
      {
        path: 'bed-inventory',
        data: { pageTitle: 'icmrDailyCountUpdation2App.bedInventory.home.title' },
        loadChildren: () => import('./bed-inventory/bed-inventory.module').then(m => m.BedInventoryModule),
      },
      {
        path: 'bed-transactions',
        data: { pageTitle: 'icmrDailyCountUpdation2App.bedTransactions.home.title' },
        loadChildren: () => import('./bed-transactions/bed-transactions.module').then(m => m.BedTransactionsModule),
      },
      {
        path: 'inventory',
        data: { pageTitle: 'icmrDailyCountUpdation2App.inventory.home.title' },
        loadChildren: () => import('./inventory/inventory.module').then(m => m.InventoryModule),
      },
      {
        path: 'inventory-used',
        data: { pageTitle: 'icmrDailyCountUpdation2App.inventoryUsed.home.title' },
        loadChildren: () => import('./inventory-used/inventory-used.module').then(m => m.InventoryUsedModule),
      },
      {
        path: 'supplier',
        data: { pageTitle: 'icmrDailyCountUpdation2App.supplier.home.title' },
        loadChildren: () => import('./supplier/supplier.module').then(m => m.SupplierModule),
      },
      {
        path: 'transactions',
        data: { pageTitle: 'icmrDailyCountUpdation2App.transactions.home.title' },
        loadChildren: () => import('./transactions/transactions.module').then(m => m.TransactionsModule),
      },
      {
        path: 'trip',
        data: { pageTitle: 'icmrDailyCountUpdation2App.trip.home.title' },
        loadChildren: () => import('./trip/trip.module').then(m => m.TripModule),
      },
      {
        path: 'trip-details',
        data: { pageTitle: 'icmrDailyCountUpdation2App.tripDetails.home.title' },
        loadChildren: () => import('./trip-details/trip-details.module').then(m => m.TripDetailsModule),
      },
      {
        path: 'contact-type',
        data: { pageTitle: 'icmrDailyCountUpdation2App.contactType.home.title' },
        loadChildren: () => import('./contact-type/contact-type.module').then(m => m.ContactTypeModule),
      },
      {
        path: 'contact',
        data: { pageTitle: 'icmrDailyCountUpdation2App.contact.home.title' },
        loadChildren: () => import('./contact/contact.module').then(m => m.ContactModule),
      },
      {
        path: 'audit-type',
        data: { pageTitle: 'icmrDailyCountUpdation2App.auditType.home.title' },
        loadChildren: () => import('./audit-type/audit-type.module').then(m => m.AuditTypeModule),
      },
      {
        path: 'audit-system',
        data: { pageTitle: 'icmrDailyCountUpdation2App.auditSystem.home.title' },
        loadChildren: () => import('./audit-system/audit-system.module').then(m => m.AuditSystemModule),
      },
      {
        path: 'icmr-daily-count',
        data: { pageTitle: 'icmrDailyCountUpdation2App.iCMRDailyCount.home.title' },
        loadChildren: () => import('./icmr-daily-count/icmr-daily-count.module').then(m => m.ICMRDailyCountModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
