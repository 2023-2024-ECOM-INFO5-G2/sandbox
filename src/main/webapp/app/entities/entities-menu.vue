<template>
  <div>
    <b-dropdown-item to="/patient">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.patient')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/aide-soignant">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.aideSoignant')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/infirmiere">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.infirmiere')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/admin">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.admin')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/repas">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.repas')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/medecin">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.medecin')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
