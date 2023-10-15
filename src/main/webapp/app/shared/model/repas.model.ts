import { type IPatient } from '@/shared/model/patient.model';

export interface IRepas {
  id?: number;
  idRepas?: number;
  nom?: string;
  description?: string;
  apportCalorique?: number;
  idRepas?: IPatient[] | null;
}

export class Repas implements IRepas {
  constructor(
    public id?: number,
    public idRepas?: number,
    public nom?: string,
    public description?: string,
    public apportCalorique?: number,
    public idRepas?: IPatient[] | null
  ) {}
}
