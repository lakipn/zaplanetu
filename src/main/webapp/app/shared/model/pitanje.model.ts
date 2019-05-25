import { IOdgovor } from 'app/shared/model/odgovor.model';

export interface IPitanje {
  id?: number;
  tekst?: string;
  redniBroj?: number;
  sekcijaNaziv?: string;
  sekcijaId?: number;
  odgovoris?: IOdgovor[];
}

export class Pitanje implements IPitanje {
  constructor(
    public id?: number,
    public tekst?: string,
    public redniBroj?: number,
    public sekcijaNaziv?: string,
    public sekcijaId?: number,
    public odgovoris?: IOdgovor[]
  ) {}
}
