export const enum TipPoena {
  RECIKLAZA = 'RECIKLAZA',
  SADNJA = 'SADNJA',
  ENERGIJA = 'ENERGIJA'
}

export interface IPoen {
  id?: number;
  tip?: TipPoena;
  korisnikId?: number;
  drzavaId?: number;
}

export class Poen implements IPoen {
  constructor(public id?: number, public tip?: TipPoena, public korisnikId?: number, public drzavaId?: number) {}
}
