<template>
  <div class="home row">
    <div class="col-md-3">
      <h6 class="card-header">Etablissements</h6>
      <ul class="list-group">
        <li
          class="list-group-item"
          v-for="etablissement in etablissements"
          :key="etablissement.id"
          @click="() => selectEtablissement(etablissement)"
          :class="{ active: selectedetablissement === etablissement }"
        >
          {{ etablissement.nom }}
        </li>
      </ul>
    </div>
    <div class="col-md-9">
      <h1 class="display-4" v-text="t$('home.title')"></h1>
      <p class="lead" v-text="t$('home.subtitle')"></p>

      <div>
        <div class="alert alert-success" v-if="authenticated">
          <span v-if="username" v-text="t$('home.logged.message', { username: username })"></span>
        </div>

        <div class="alert alert-warning" v-if="!authenticated">
          <span v-text="t$('global.messages.info.authenticated.prefix')"></span>
          <a class="alert-link" v-on:click="openLogin()" v-text="t$('global.messages.info.authenticated.link')"></a
          ><span v-html="t$('global.messages.info.authenticated.suffix')"></span>
        </div>
        <div class="alert alert-warning" v-if="!authenticated">
          <span v-text="t$('global.messages.info.register.noaccount')"></span>&nbsp;
          <router-link class="alert-link" to="/register" v-text="t$('global.messages.info.register.link')"></router-link>
        </div>
      </div>
      <div class="col">
        <div class="card" v-if="selectedetablissement.id">
          <div class="card-body">
            <h5>{{ selectedetablissement.nom }}</h5>
            <h5>{{ selectedetablissement.adresse + ' ' + selectedetablissement.ville }}</h5>
          </div>
          <router-link
            :to="{ name: 'EtablissementView', params: { etablissementId: selectedetablissement.id } }"
            custom
            v-slot="{ navigate }"
          >
            <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
              <font-awesome-icon icon="eye"></font-awesome-icon>
              <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
            </button>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>

<style>
.list-group-item {
  cursor: pointer;
}
</style>
