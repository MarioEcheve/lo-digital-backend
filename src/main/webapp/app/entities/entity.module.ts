import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'entidad',
        loadChildren: () => import('./entidad/entidad.module').then(m => m.BackendEntidadModule)
      },
      {
        path: 'actividad-rubro',
        loadChildren: () => import('./actividad-rubro/actividad-rubro.module').then(m => m.BackendActividadRubroModule)
      },
      {
        path: 'tipo-entidad',
        loadChildren: () => import('./tipo-entidad/tipo-entidad.module').then(m => m.BackendTipoEntidadModule)
      },
      {
        path: 'dependencia',
        loadChildren: () => import('./dependencia/dependencia.module').then(m => m.BackendDependenciaModule)
      },
      {
        path: 'usuario-dependencia',
        loadChildren: () => import('./usuario-dependencia/usuario-dependencia.module').then(m => m.BackendUsuarioDependenciaModule)
      },
      {
        path: 'perfil-usuario-dependencia',
        loadChildren: () =>
          import('./perfil-usuario-dependencia/perfil-usuario-dependencia.module').then(m => m.BackendPerfilUsuarioDependenciaModule)
      },
      {
        path: 'tipo-usuario-dependencia',
        loadChildren: () =>
          import('./tipo-usuario-dependencia/tipo-usuario-dependencia.module').then(m => m.BackendTipoUsuarioDependenciaModule)
      },
      {
        path: 'contrato',
        loadChildren: () => import('./contrato/contrato.module').then(m => m.BackendContratoModule)
      },
      {
        path: 'estado-servicio',
        loadChildren: () => import('./estado-servicio/estado-servicio.module').then(m => m.BackendEstadoServicioModule)
      },
      {
        path: 'tipo-moneda',
        loadChildren: () => import('./tipo-moneda/tipo-moneda.module').then(m => m.BackendTipoMonedaModule)
      },
      {
        path: 'tipo-monto',
        loadChildren: () => import('./tipo-monto/tipo-monto.module').then(m => m.BackendTipoMontoModule)
      },
      {
        path: 'tipo-contrato',
        loadChildren: () => import('./tipo-contrato/tipo-contrato.module').then(m => m.BackendTipoContratoModule)
      },
      {
        path: 'modalidad',
        loadChildren: () => import('./modalidad/modalidad.module').then(m => m.BackendModalidadModule)
      },
      {
        path: 'libro',
        loadChildren: () => import('./libro/libro.module').then(m => m.BackendLibroModule)
      },
      {
        path: 'estado-libro',
        loadChildren: () => import('./estado-libro/estado-libro.module').then(m => m.BackendEstadoLibroModule)
      },
      {
        path: 'tipo-firma',
        loadChildren: () => import('./tipo-firma/tipo-firma.module').then(m => m.BackendTipoFirmaModule)
      },
      {
        path: 'folio',
        loadChildren: () => import('./folio/folio.module').then(m => m.BackendFolioModule)
      },
      {
        path: 'estado-respuesta',
        loadChildren: () => import('./estado-respuesta/estado-respuesta.module').then(m => m.BackendEstadoRespuestaModule)
      },
      {
        path: 'tipo-libro',
        loadChildren: () => import('./tipo-libro/tipo-libro.module').then(m => m.BackendTipoLibroModule)
      },
      {
        path: 'tipo-folio',
        loadChildren: () => import('./tipo-folio/tipo-folio.module').then(m => m.BackendTipoFolioModule)
      },
      {
        path: 'usuario-libro',
        loadChildren: () => import('./usuario-libro/usuario-libro.module').then(m => m.BackendUsuarioLibroModule)
      },
      {
        path: 'archivo',
        loadChildren: () => import('./archivo/archivo.module').then(m => m.BackendArchivoModule)
      },
      {
        path: 'ges-alerta',
        loadChildren: () => import('./ges-alerta/ges-alerta.module').then(m => m.BackendGesAlertaModule)
      },
      {
        path: 'ges-nota',
        loadChildren: () => import('./ges-nota/ges-nota.module').then(m => m.BackendGesNotaModule)
      },
      {
        path: 'ges-favorito',
        loadChildren: () => import('./ges-favorito/ges-favorito.module').then(m => m.BackendGesFavoritoModule)
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.BackendRegionModule)
      },
      {
        path: 'comuna',
        loadChildren: () => import('./comuna/comuna.module').then(m => m.BackendComunaModule)
      },
      {
        path: 'usuario',
        loadChildren: () => import('./usuario/usuario.module').then(m => m.BackendUsuarioModule)
      },
      {
        path: 'usuario-libro-perfil',
        loadChildren: () => import('./usuario-libro-perfil/usuario-libro-perfil.module').then(m => m.BackendUsuarioLibroPerfilModule)
      },
      {
        path: 'folio-referencia',
        loadChildren: () => import('./folio-referencia/folio-referencia.module').then(m => m.BackendFolioReferenciaModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BackendEntityModule {}
