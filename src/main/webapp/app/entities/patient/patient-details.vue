<template>
  <div class="row">
    <div class="col-md-11 py-1">
      <font-awesome-icon icon="user"></font-awesome-icon>
      <span class="h3">
        {{ patient.prenom }}
      </span>
      &nbsp;
      <span class="h3 text-uppercase">
        <strong>
          {{ patient.nom }}
        </strong>
      </span>
    </div>
    <div class="col-md-1">
      <router-link v-if="patient.id" :to="{ name: 'PatientEdit', params: { patientId: patient.id } }" custom v-slot="{ navigate }">
        <button @click="navigate" class="btn btn-primary">
          <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
        </button>
      </router-link>
    </div>
    <div class="col-6 py-1">
      <font-awesome-icon :icon="['fas', 'cake-candles']" />
      <span class="h6">{{ patient.dateDeNaissance }}</span>
    </div>
    <div class="col-6 py-1">
      <font-awesome-icon :icon="['fas', 'genderless']" />
      <span class="h6">{{ patient.sexe }}</span>
    </div>
    <div class="col-6 py-1">
      <font-awesome-icon :icon="['fas', 'location-dot']" />
      <span class="h6">Chambre {{ patient.numChambre }}</span>
    </div>
    <div class="col-6 py-1">
      <font-awesome-icon :icon="['fas', 'arrows-up-down']" />
      <span class="h6">{{ patient.taille }}cm</span>
    </div>
    <div class="col-6 py-1">
      <font-awesome-icon :icon="['fas', 'door-open']" />
      <span class="h6">{{ patient.dateArrivee }}</span>
    </div>
  </div>

  <div class="row justify-content-center mt-5">
    <div class="col-12">
      <div class="card">
        <div class="card-body">
          <h4 class="card-title" v-text="t$('g2EcomApp.patient.infoComplementaires')"></h4>
          <p class="card-text">
            {{ patient.infoComplementaires }}
          </p>
        </div>
      </div>
    </div>
  </div>
  <div class="row justify-content-center text-center mt-5">
    <div class="col-4" v-if="poidsPatient.length > 0">
      <div class="card">
        <div class="card-header">
          <h6>Poids (kg)</h6>
          <a href="#" class="btn btn-primary" @click="addPoidsValue">+</a>
        </div>
        <div class="card-body">
          <h5>{{ poidsPatient[poidsPatient.length - 1].valeur }}</h5>
        </div>
      </div>
    </div>
    <div class="col-4" v-if="EPAPatient.length > 0">
      <div class="card">
        <div class="card-header">
          <h6>EPA</h6>
          <a href="#" class="btn btn-primary" @click="addEPAValue">+</a>
        </div>
        <div class="card-body">
          <h5>{{ EPAPatient[EPAPatient.length - 1].valeur }}</h5>
        </div>
      </div>
    </div>
    <div class="col-4">
      <div class="card">
        <div class="card-header">
          <h6>IMC</h6>
        </div>
        <div class="card-body">
          <h5>{{ patientIMC }}</h5>
        </div>
      </div>
    </div>
  </div>
  <div class="row justify-content-center text-center mt-5">
    <div class="col" v-if="weightChartLoaded">
      <Line id="my-chart-id" :options="chartOptions" :data="weightChartData" />
    </div>
    <div class="col" v-if="EPAChartLoaded">
      <Line id="my-chart-id" :options="chartOptions" :data="EPAChartData" />
    </div>
  </div>
  <!--    <div class="col-8">-->
  <!--      <div v-if="patient">-->
  <!--        <h2 class="jh-entity-heading" data-cy="patientDetailsHeading">-->
  <!--          <span v-text="t$('g2EcomApp.patient.detail.title')"></span> {{ patient.id }}-->
  <!--        </h2>-->
  <!--        <dl class="row jh-entity-details">-->
  <!--          <dt>-->
  <!--            <span v-text="t$('g2EcomApp.patient.prenom')"></span>-->
  <!--          </dt>-->
  <!--          <dd>-->
  <!--            <span>{{ patient.prenom }}</span>-->
  <!--          </dd>-->
  <!--          <dt>-->
  <!--            <span v-text="t$('g2EcomApp.patient.nom')"></span>-->
  <!--          </dt>-->
  <!--          <dd>-->
  <!--            <span>{{ patient.nom }}</span>-->
  <!--          </dd>-->
  <!--          <dt>-->
  <!--            <span v-text="t$('g2EcomApp.patient.sexe')"></span>-->
  <!--          </dt>-->
  <!--          <dd>-->
  <!--            <span>{{ patient.sexe }}</span>-->
  <!--          </dd>-->
  <!--          <dt>-->
  <!--            <span v-text="t$('g2EcomApp.patient.dateDeNaissance')"></span>-->
  <!--          </dt>-->
  <!--          <dd>-->
  <!--            <span>{{ patient.dateDeNaissance }}</span>-->
  <!--          </dd>-->
  <!--          <dt>-->
  <!--            <span v-text="t$('g2EcomApp.patient.numChambre')"></span>-->
  <!--          </dt>-->
  <!--          <dd>-->
  <!--            <span>{{ patient.numChambre }}</span>-->
  <!--          </dd>-->
  <!--          <dt>-->
  <!--            <span v-text="t$('g2EcomApp.patient.taille')"></span>-->
  <!--          </dt>-->
  <!--          <dd>-->
  <!--            <span>{{ patient.taille }}</span>-->
  <!--          </dd>-->
  <!--          <dt>-->
  <!--            <span v-text="t$('g2EcomApp.patient.dateArrivee')"></span>-->
  <!--          </dt>-->
  <!--          <dd>-->
  <!--            <span>{{ patient.dateArrivee }}</span>-->
  <!--          </dd>-->
  <!--          <dt>-->
  <!--            <span v-text="t$('g2EcomApp.patient.infoComplementaires')"></span>-->
  <!--          </dt>-->
  <!--          <dd>-->
  <!--            <span>{{ patient.infoComplementaires }}</span>-->
  <!--          </dd>-->
  <!--          <dt>-->
  <!--            <span v-text="t$('g2EcomApp.patient.medecin')"></span>-->
  <!--          </dt>-->
  <!--          <dd>-->
  <!--            <div v-if="patient.medecin">-->
  <!--              <router-link :to="{ name: 'MedecinView', params: { medecinId: patient.medecin.id } }">{{ patient.medecin.id }}</router-link>-->
  <!--            </div>-->
  <!--          </dd>-->
  <!--          <dt>-->
  <!--            <span v-text="t$('g2EcomApp.patient.etablissement')"></span>-->
  <!--          </dt>-->
  <!--          <dd>-->
  <!--            <div v-if="patient.etablissement">-->
  <!--              <router-link :to="{ name: 'EtablissementView', params: { etablissementId: patient.etablissement.id } }">{{-->
  <!--                patient.etablissement.id-->
  <!--              }}</router-link>-->
  <!--            </div>-->
  <!--          </dd>-->
  <!--        </dl>-->
  <!--        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">-->
  <!--          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>-->
  <!--        </button>-->
  <!--          <router-link v-if="patient.id" :to="{ name: 'PatientEdit', params: { patientId: patient.id } }" custom v-slot="{ navigate }">-->
  <!--            <button @click="navigate" class="btn btn-primary">-->
  <!--              <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>-->
  <!--            </button>-->
  <!--          </router-link>-->
  <!--      </div>-->
  <!--    </div>-->
</template>

<script lang="ts" src="./patient-details.component.ts"></script>
