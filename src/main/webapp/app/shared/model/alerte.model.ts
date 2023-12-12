import { type IPatient } from '@/shared/model/patient.model';

export interface IAlerte {
  id?: number;
  description?: string;
  date?: Date;
  patient?: IPatient | null;
}

export class Alerte implements IAlerte {
  constructor(
    public id?: number,
    public description?: string,
    public date?: Date,
    public patient?: IPatient | null,
  ) {}
}
