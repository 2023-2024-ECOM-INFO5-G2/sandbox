<template>
  <div class="home row">
    <div class="col-md-3">
      <h6 class="card-header">Etablissements</h6>

      <select v-model="selectedetablissement" class="form-select">
        <option v-for="etablissement in etablissements" :value="etablissement" :key="etablissement.id">
          {{ etablissement.nom }}
        </option>
      </select>
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
        <div class="card" v-if="selectedetablissement">
          <div class="card-body">
            <h5>{{ selectedetablissement.nom }}</h5>
            <h5>{{ selectedetablissement.adresse + ' ' + selectedetablissement.ville }}</h5>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
