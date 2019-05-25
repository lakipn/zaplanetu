export interface IDrzava {
  id?: number;
  naziv?: string;
  slikaContentType?: string;
  slika?: any;
}

export class Drzava implements IDrzava {
  constructor(public id?: number, public naziv?: string, public slikaContentType?: string, public slika?: any) {}
}
