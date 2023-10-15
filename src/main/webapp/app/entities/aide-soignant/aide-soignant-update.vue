<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="ecom02App.aideSoignant.home.createOrEditLabel"
          data-cy="AideSoignantCreateUpdateHeading"
          v-text="t$('ecom02App.aideSoignant.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="aideSoignant.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="aideSoignant.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ecom02App.aideSoignant.idSoignant')" for="aide-soignant-idSoignant"></label>
            <input
              type="number"
              class="form-control"
              name="idSoignant"
              id="aide-soignant-idSoignant"
              data-cy="idSoignant"
              :class="{ valid: !v$.idSoignant.$invalid, invalid: v$.idSoignant.$invalid }"
              v-model.number="v$.idSoignant.$model"
              required
            />
            <div v-if="v$.idSoignant.$anyDirty && v$.idSoignant.$invalid">
              <small class="form-text text-danger" v-for="error of v$.idSoignant.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('ecom02App.aideSoignant.prenom')" for="aide-soignant-prenom"></label>
            <input
              type="text"
              class="form-control"
              name="prenom"
              id="aide-soignant-prenom"
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
            <label class="form-control-label" v-text="t$('ecom02App.aideSoignant.nom')" for="aide-soignant-nom"></label>
            <input
              type="text"
              class="form-control"
              name="nom"
              id="aide-soignant-nom"
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
            <label v-text="t$('ecom02App.aideSoignant.idSoignant')" for="aide-soignant-idSoignant"></label>
            <select
              class="form-control"
              id="aide-soignant-idSoignants"
              data-cy="idSoignant"
              multiple
              name="idSoignant"
              v-if="aideSoignant.idSoignants !== undefined"
              v-model="aideSoignant.idSoignants"
            >
              <option
                v-bind:value="getSelected(aideSoignant.idSoignants, patientOption)"
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
<script lang="ts" src="./aide-soignant-update.component.ts"></script>
