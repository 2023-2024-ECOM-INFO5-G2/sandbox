export interface IPatient {
  id?: number;
  prenom?: string;
  nom?: string;
  sexe?: string;
  dateDeNaissance?: Date;
  chambre?: number;
  poids?: number | null;
  dateArrivee?: Date;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public prenom?: string,
    public nom?: string,
    public sexe?: string,
    public dateDeNaissance?: Date,
    public chambre?: number,
    public poids?: number | null,
    public dateArrivee?: Date,
  ) {}
}
