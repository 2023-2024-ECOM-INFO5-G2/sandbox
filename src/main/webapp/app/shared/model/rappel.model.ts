import { type IUser } from '@/shared/model/user.model';
import { type IPatient } from '@/shared/model/patient.model';

export interface IRappel {
  id?: number;
  date?: Date;
  frequenceJour?: number;
  echeance?: Date;
  tache?: string;
  users?: IUser[] | null;
  patient?: IPatient | null;
}

export class Rappel implements IRappel {
  constructor(
    public id?: number,
    public date?: Date,
    public frequenceJour?: number,
    public echeance?: Date,
    public tache?: string,
    public users?: IUser[] | null,
    public patient?: IPatient | null,
  ) {}
}
