/**
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { SlotsDto } from './slotsDto';

export interface CreateShopRequestDto { 
    addressSupplement?: string;
    city?: string;
    contactTypes?: { [key: string]: string; };
    details?: string;
    name?: string;
    ownerName?: string;
    password?: string;
    slots?: SlotsDto;
    street?: string;
    website?: string;
    zipCode?: string;
}