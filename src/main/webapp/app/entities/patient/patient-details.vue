<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="patient">
        <h2 class="jh-entity-heading" data-cy="patientDetailsHeading">
          <span v-text="t$('polytechEcomG2App.patient.detail.title')"></span> {{ patient.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('polytechEcomG2App.patient.prenom')"></span>
          </dt>
          <dd>
            <span>{{ patient.prenom }}</span>
          </dd>
          <dt>
            <span v-text="t$('polytechEcomG2App.patient.nom')"></span>
          </dt>
          <dd>
            <span>{{ patient.nom }}</span>
          </dd>
          <dt>
            <span v-text="t$('polytechEcomG2App.patient.sexe')"></span>
          </dt>
          <dd>
            <span>{{ patient.sexe }}</span>
          </dd>
          <dt>
            <span v-text="t$('polytechEcomG2App.patient.dateDeNaissance')"></span>
          </dt>
          <dd>
            <span>{{ patient.dateDeNaissance }}</span>
          </dd>
          <dt>
            <span v-text="t$('polytechEcomG2App.patient.chambre')"></span>
          </dt>
          <dd>
            <span>{{ patient.chambre }}</span>
          </dd>
          <dt>
            <span v-text="t$('polytechEcomG2App.patient.poids')"></span>
          </dt>
          <dd>
            <span>{{ patient.poids }}</span>
          </dd>
          <dt>
            <span v-text="t$('polytechEcomG2App.patient.dateArrivee')"></span>
          </dt>
          <dd>
            <span>{{ patient.dateArrivee }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="patient.id" :to="{ name: 'PatientEdit', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./patient-details.component.ts"></script>
