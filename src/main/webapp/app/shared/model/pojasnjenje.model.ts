export interface IPojasnjenje {
  id?: number;
  tekst?: any;
  odgovorId?: number;
}

export class Pojasnjenje implements IPojasnjenje {
  constructor(public id?: number, public tekst?: any, public odgovorId?: number) {}
}
