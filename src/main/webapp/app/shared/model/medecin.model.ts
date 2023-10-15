import { type IPatient } from '@/shared/model/patient.model';

export interface IMedecin {
  id?: number;
  idMedecin?: number;
  firstName?: string;
  lastName?: string;
  idMedecins?: IPatient[] | null;
}

export class Medecin implements IMedecin {
  constructor(
    public id?: number,
    public idMedecin?: number,
    public firstName?: string,
    public lastName?: string,
    public idMedecins?: IPatient[] | null
  ) {}
}
