<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecom02App.medecin.home.createOrEditLabel"
          data-cy="MedecinCreateUpdateHeading"
          v-text="t$('ecom02App.medecin.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="medecin.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="medecin.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ecom02App.medecin.idMedecin')" for="medecin-idMedecin"></label>
            <input
              type="number"
              class="form-control"
              name="idMedecin"
              id="medecin-idMedecin"
              data-cy="idMedecin"
              :class="{ valid: !v$.idMedecin.$invalid, invalid: v$.idMedecin.$invalid }"
              v-model.number="v$.idMedecin.$model"
              required
            />
            <div v-if="v$.idMedecin.$anyDirty && v$.idMedecin.$invalid">
              <small class="form-text text-danger" v-for="error of v$.idMedecin.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ecom02App.medecin.firstName')" for="medecin-firstName"></label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="medecin-firstName"
              data-cy="firstName"
              :class="{ valid: !v$.firstName.$invalid, invalid: v$.firstName.$invalid }"
              v-model="v$.firstName.$model"
              required
            />
            <div v-if="v$.firstName.$anyDirty && v$.firstName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.firstName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ecom02App.medecin.lastName')" for="medecin-lastName"></label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="medecin-lastName"
              data-cy="lastName"
              :class="{ valid: !v$.lastName.$invalid, invalid: v$.lastName.$invalid }"
              v-model="v$.lastName.$model"
              required
            />
            <div v-if="v$.lastName.$anyDirty && v$.lastName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.lastName.$errors" :key="error.$uid">{{ error.$message }}</small>
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
<script lang="ts" src="./medecin-update.component.ts"></script>
