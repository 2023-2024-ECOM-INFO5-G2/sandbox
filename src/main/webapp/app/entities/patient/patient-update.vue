<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="blogApp.patient.home.createOrEditLabel"
          data-cy="PatientCreateUpdateHeading"
          v-text="t$('blogApp.patient.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="patient.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="patient.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('blogApp.patient.prenom')" for="patient-prenom"></label>
            <input
              type="text"
              class="form-control"
              name="prenom"
              id="patient-prenom"
              data-cy="prenom"
              :class="{ valid: !v$.prenom.$invalid, invalid: v$.prenom.$invalid }"
              v-model="v$.prenom.$model"
              required
            />
            <div v-if="v$.prenom.$anyDirty && v$.prenom.$invalid">
              <small class="form-text text-danger" v-for="error of v$.prenom.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('blogApp.patient.nom')" for="patient-nom"></label>
            <input
              type="text"
              class="form-control"
              name="nom"
              id="patient-nom"
              data-cy="nom"
              :class="{ valid: !v$.nom.$invalid, invalid: v$.nom.$invalid }"
              v-model="v$.nom.$model"
              required
            />
            <div v-if="v$.nom.$anyDirty && v$.nom.$invalid">
              <small class="form-text text-danger" v-for="error of v$.nom.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('blogApp.patient.sexe')" for="patient-sexe"></label>
            <input
              type="text"
              class="form-control"
              name="sexe"
              id="patient-sexe"
              data-cy="sexe"
              :class="{ valid: !v$.sexe.$invalid, invalid: v$.sexe.$invalid }"
              v-model="v$.sexe.$model"
              required
            />
            <div v-if="v$.sexe.$anyDirty && v$.sexe.$invalid">
              <small class="form-text text-danger" v-for="error of v$.sexe.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('blogApp.patient.dateDeNaissance')" for="patient-dateDeNaissance"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="patient-dateDeNaissance"
                  v-model="v$.dateDeNaissance.$model"
                  name="dateDeNaissance"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="patient-dateDeNaissance"
                data-cy="dateDeNaissance"
                type="text"
                class="form-control"
                name="dateDeNaissance"
                :class="{ valid: !v$.dateDeNaissance.$invalid, invalid: v$.dateDeNaissance.$invalid }"
                v-model="v$.dateDeNaissance.$model"
                required
              />
            </b-input-group>
            <div v-if="v$.dateDeNaissance.$anyDirty && v$.dateDeNaissance.$invalid">
              <small class="form-text text-danger" v-for="error of v$.dateDeNaissance.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('blogApp.patient.chambre')" for="patient-chambre"></label>
            <input
              type="number"
              class="form-control"
              name="chambre"
              id="patient-chambre"
              data-cy="chambre"
              :class="{ valid: !v$.chambre.$invalid, invalid: v$.chambre.$invalid }"
              v-model.number="v$.chambre.$model"
              required
            />
            <div v-if="v$.chambre.$anyDirty && v$.chambre.$invalid">
              <small class="form-text text-danger" v-for="error of v$.chambre.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('blogApp.patient.poids')" for="patient-poids"></label>
            <input
              type="number"
              class="form-control"
              name="poids"
              id="patient-poids"
              data-cy="poids"
              :class="{ valid: !v$.poids.$invalid, invalid: v$.poids.$invalid }"
              v-model.number="v$.poids.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('blogApp.patient.dateArrivee')" for="patient-dateArrivee"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="patient-dateArrivee"
                  v-model="v$.dateArrivee.$model"
                  name="dateArrivee"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="patient-dateArrivee"
                data-cy="dateArrivee"
                type="text"
                class="form-control"
                name="dateArrivee"
                :class="{ valid: !v$.dateArrivee.$invalid, invalid: v$.dateArrivee.$invalid }"
                v-model="v$.dateArrivee.$model"
                required
              />
            </b-input-group>
            <div v-if="v$.dateArrivee.$anyDirty && v$.dateArrivee.$invalid">
              <small class="form-text text-danger" v-for="error of v$.dateArrivee.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./patient-update.component.ts"></script>
