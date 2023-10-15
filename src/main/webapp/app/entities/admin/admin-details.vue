<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="admin">
        <h2 class="jh-entity-heading" data-cy="adminDetailsHeading">
          <span v-text="t$('ecom02App.admin.detail.title')"></span> {{ admin.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('ecom02App.admin.idAdmin')"></span>
          </dt>
          <dd>
            <span>{{ admin.idAdmin }}</span>
          </dd>
          <dt>
            <span v-text="t$('ecom02App.admin.prenom')"></span>
          </dt>
          <dd>
            <span>{{ admin.prenom }}</span>
          </dd>
          <dt>
            <span v-text="t$('ecom02App.admin.nom')"></span>
          </dt>
          <dd>
            <span>{{ admin.nom }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="admin.id" :to="{ name: 'AdminEdit', params: { adminId: admin.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./admin-details.component.ts"></script>
