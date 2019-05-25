export interface IOdgovor {
  id?: number;
  tekst?: string;
  tacan?: boolean;
  pojasnjenjeId?: number;
  pitanjeId?: number;
}

export class Odgovor implements IOdgovor {
  constructor(public id?: number, public tekst?: string, public tacan?: boolean, public pojasnjenjeId?: number, public pitanjeId?: number) {
    this.tacan = this.tacan || false;
  }
}
