export interface IAdmin {
  id?: number;
  idAdmin?: number;
  prenom?: string;
  nom?: string;
}

export class Admin implements IAdmin {
  constructor(public id?: number, public idAdmin?: number, public prenom?: string, public nom?: string) {}
}
