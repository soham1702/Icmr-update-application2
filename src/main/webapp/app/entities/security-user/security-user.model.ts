import dayjs from 'dayjs/esm';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';

export interface ISecurityUser {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  designation?: string | null;
  login?: string;
  passwordHash?: string;
  email?: string | null;
  imageUrl?: string | null;
  activated?: boolean;
  langKey?: string | null;
  activationKey?: string | null;
  resetKey?: string | null;
  resetDate?: dayjs.Dayjs | null;
  mobileNo?: string | null;
  oneTimePassword?: string | null;
  otpExpiryTime?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs;
  lastModifiedBy?: string;
  securityPermissions?: ISecurityPermission[] | null;
  securityRoles?: ISecurityRole[] | null;
}

export class SecurityUser implements ISecurityUser {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public designation?: string | null,
    public login?: string,
    public passwordHash?: string,
    public email?: string | null,
    public imageUrl?: string | null,
    public activated?: boolean,
    public langKey?: string | null,
    public activationKey?: string | null,
    public resetKey?: string | null,
    public resetDate?: dayjs.Dayjs | null,
    public mobileNo?: string | null,
    public oneTimePassword?: string | null,
    public otpExpiryTime?: dayjs.Dayjs | null,
    public lastModified?: dayjs.Dayjs,
    public lastModifiedBy?: string,
    public securityPermissions?: ISecurityPermission[] | null,
    public securityRoles?: ISecurityRole[] | null
  ) {
    this.activated = this.activated ?? false;
  }
}

export function getSecurityUserIdentifier(securityUser: ISecurityUser): number | undefined {
  return securityUser.id;
}
