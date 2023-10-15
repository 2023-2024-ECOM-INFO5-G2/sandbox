<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="infirmiere">
        <h2 class="jh-entity-heading" data-cy="infirmiereDetailsHeading">
          <span v-text="t$('ecom02App.infirmiere.detail.title')"></span> {{ infirmiere.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('ecom02App.infirmiere.idInfirmiere')"></span>
          </dt>
          <dd>
            <span>{{ infirmiere.idInfirmiere }}</span>
          </dd>
          <dt>
            <span v-text="t$('ecom02App.infirmiere.prenom')"></span>
          </dt>
          <dd>
            <span>{{ infirmiere.prenom }}</span>
          </dd>
          <dt>
            <span v-text="t$('ecom02App.infirmiere.nom')"></span>
          </dt>
          <dd>
            <span>{{ infirmiere.nom }}</span>
          </dd>
          <dt>
            <span v-text="t$('ecom02App.infirmiere.idInfirmiere')"></span>
          </dt>
          <dd>
            <span v-for="(idInfirmiere, i) in infirmiere.idInfirmieres" :key="idInfirmiere.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'PatientView', params: { patientId: idInfirmiere.id } }">{{ idInfirmiere.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="infirmiere.id"
          :to="{ name: 'InfirmiereEdit', params: { infirmiereId: infirmiere.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./infirmiere-details.component.ts"></script>
