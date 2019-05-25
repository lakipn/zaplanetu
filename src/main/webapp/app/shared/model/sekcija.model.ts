import { IPitanje } from 'app/shared/model/pitanje.model';

export interface ISekcija {
  id?: number;
  naziv?: string;
  slikaContentType?: string;
  slika?: any;
  pojasnjenje?: any;
  pitanjas?: IPitanje[];
}

export class Sekcija implements ISekcija {
  constructor(
    public id?: number,
    public naziv?: string,
    public slikaContentType?: string,
    public slika?: any,
    public pojasnjenje?: any,
    public pitanjas?: IPitanje[]
  ) {}
}
