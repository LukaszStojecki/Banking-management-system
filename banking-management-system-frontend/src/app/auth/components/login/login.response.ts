export interface LoginResponse {
  authenticationToken: string;
  refreshToken: string;
  expiryDuration: Date;
  identificationNumber: string;
}
