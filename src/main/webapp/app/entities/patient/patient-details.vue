<template>
  <div class="row">
    <div class="col-2">
      <img
        src="https://this-person-does-not-exist.com/img/avatar-gen1157cd8fd17bddfbf222d6236f79640c.jpg"
        alt="image patient"
        height="250"
        width="250"
      />
    </div>
    <div class="col-5">
      <div class="row">
        <div class="col-6 py-1">Nom : Robert</div>
        <div class="col-6 py-1">Prénom : Robert</div>
        <div class="col-6 py-1">Date de naissance : 01/01/1952</div>
        <div class="col-6 py-1">Sexe: Femme</div>
        <div class="col-6 py-1">Chambre: 123</div>
        <div class="col-6 py-1">Taille : 173cm</div>
      </div>
    </div>
  </div>

  <div class="row justify-content-center mt-5">
    <div class="col card">
      <div class="card-body">
        <h4 class="card-title">Informations complémentaire</h4>
        <p class="card-text">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean turpis nulla, commodo ut efficitur sed, consequat et nulla.
          Aliquam ut lectus non mauris egestas maximus. In nec molestie sem, in sagittis felis. Vestibulum ultricies et nibh vel congue.
          Duis varius finibus enim sit amet aliquet. Nunc quis ipsum blandit dolor euismod mollis. Pellentesque vulputate dapibus ultrices.
          Nam ac eros non ante volutpat feugiat.
        </p>
      </div>
    </div>
  </div>
  <div class="row justify-content-center mt-5 text-center">
    <div class="col card">
      <h5 class="card-header">Poids</h5>
      <div class="card-body">
        <h4>54kg</h4>
      </div>
    </div>
    <div class="col card">
      <h5 class="card-header">EPA</h5>
      <div class="card-body">
        <h4>6kg</h4>
      </div>
    </div>
    <div class="col card">
      <h5 class="card-header">IMC</h5>
      <div class="card-body">
        <h4>18kg</h4>
      </div>
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
  <!--        <router-link v-if="patient.id" :to="{ name: 'PatientEdit', params: { patientId: patient.id } }" custom v-slot="{ navigate }">-->
  <!--          <button @click="navigate" class="btn btn-primary">-->
  <!--            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>-->
  <!--          </button>-->
  <!--        </router-link>-->
  <!--      </div>-->
  <!--    </div>-->
</template>

<script lang="ts" src="./patient-details.component.ts"></script>
