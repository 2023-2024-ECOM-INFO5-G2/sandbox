<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecom02App.infirmiere.home.createOrEditLabel"
          data-cy="InfirmiereCreateUpdateHeading"
          v-text="t$('ecom02App.infirmiere.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="infirmiere.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="infirmiere.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ecom02App.infirmiere.idInfirmiere')" for="infirmiere-idInfirmiere"></label>
            <input
              type="number"
              class="form-control"
              name="idInfirmiere"
              id="infirmiere-idInfirmiere"
              data-cy="idInfirmiere"
              :class="{ valid: !v$.idInfirmiere.$invalid, invalid: v$.idInfirmiere.$invalid }"
              v-model.number="v$.idInfirmiere.$model"
              required
            />
            <div v-if="v$.idInfirmiere.$anyDirty && v$.idInfirmiere.$invalid">
              <small class="form-text text-danger" v-for="error of v$.idInfirmiere.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ecom02App.infirmiere.prenom')" for="infirmiere-prenom"></label>
            <input
              type="text"
              class="form-control"
              name="prenom"
              id="infirmiere-prenom"
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
            <label class="form-control-label" v-text="t$('ecom02App.infirmiere.nom')" for="infirmiere-nom"></label>
            <input
              type="text"
              class="form-control"
              name="nom"
              id="infirmiere-nom"
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
            <label v-text="t$('ecom02App.infirmiere.idInfirmiere')" for="infirmiere-idInfirmiere"></label>
            <select
              class="form-control"
              id="infirmiere-idInfirmieres"
              data-cy="idInfirmiere"
              multiple
              name="idInfirmiere"
              v-if="infirmiere.idInfirmieres !== undefined"
              v-model="infirmiere.idInfirmieres"
            >
              <option
                v-bind:value="getSelected(infirmiere.idInfirmieres, patientOption)"
                v-for="patientOption in patients"
                :key="patientOption.id"
              >
                {{ patientOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./infirmiere-update.component.ts"></script>
