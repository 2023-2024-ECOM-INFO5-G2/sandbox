import { type IPatient } from '@/shared/model/patient.model';

export interface IAideSoignant {
  id?: number;
  idSoignant?: number;
  prenom?: string;
  nom?: string;
  idSoignants?: IPatient[] | null;
}

export class AideSoignant implements IAideSoignant {
  constructor(
    public id?: number,
    public idSoignant?: number,
    public prenom?: string,
    public nom?: string,
    public idSoignants?: IPatient[] | null
  ) {}
}
