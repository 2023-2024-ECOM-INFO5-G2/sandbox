import { type IPatient } from '@/shared/model/patient.model';

export interface IInfirmiere {
  id?: number;
  idInfirmiere?: number;
  prenom?: string;
  nom?: string;
  idInfirmieres?: IPatient[] | null;
}

export class Infirmiere implements IInfirmiere {
  constructor(
    public id?: number,
    public idInfirmiere?: number,
    public prenom?: string,
    public nom?: string,
    public idInfirmieres?: IPatient[] | null
  ) {}
}
