import { type IMedecin } from '@/shared/model/medecin.model';
import { type IAideSoignant } from '@/shared/model/aide-soignant.model';
import { type IInfirmiere } from '@/shared/model/infirmiere.model';
import { type IRepas } from '@/shared/model/repas.model';

export interface IPatient {
  id?: number;
  idPatient?: number;
  prenom?: string;
  nom?: string;
  sexe?: string;
  dateDeNaissance?: Date;
  chambre?: number;
  poids?: number | null;
  dateArrivee?: Date;
  idMedecin?: IMedecin | null;
  idPatients?: IAideSoignant[] | null;
  idPatients?: IInfirmiere[] | null;
  idPatients?: IRepas[] | null;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public idPatient?: number,
    public prenom?: string,
    public nom?: string,
    public sexe?: string,
    public dateDeNaissance?: Date,
    public chambre?: number,
    public poids?: number | null,
    public dateArrivee?: Date,
    public idMedecin?: IMedecin | null,
    public idPatients?: IAideSoignant[] | null,
    public idPatients?: IInfirmiere[] | null,
    public idPatients?: IRepas[] | null
  ) {}
}
