<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="aideSoignant">
        <h2 class="jh-entity-heading" data-cy="aideSoignantDetailsHeading">
          <span v-text="t$('ecom02App.aideSoignant.detail.title')"></span> {{ aideSoignant.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('ecom02App.aideSoignant.idSoignant')"></span>
          </dt>
          <dd>
            <span>{{ aideSoignant.idSoignant }}</span>
          </dd>
          <dt>
            <span v-text="t$('ecom02App.aideSoignant.prenom')"></span>
          </dt>
          <dd>
            <span>{{ aideSoignant.prenom }}</span>
          </dd>
          <dt>
            <span v-text="t$('ecom02App.aideSoignant.nom')"></span>
          </dt>
          <dd>
            <span>{{ aideSoignant.nom }}</span>
          </dd>
          <dt>
            <span v-text="t$('ecom02App.aideSoignant.idSoignant')"></span>
          </dt>
          <dd>
            <span v-for="(idSoignant, i) in aideSoignant.idSoignants" :key="idSoignant.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'PatientView', params: { patientId: idSoignant.id } }">{{ idSoignant.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="aideSoignant.id"
          :to="{ name: 'AideSoignantEdit', params: { aideSoignantId: aideSoignant.id } }"
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

<script lang="ts" src="./aide-soignant-details.component.ts"></script>
