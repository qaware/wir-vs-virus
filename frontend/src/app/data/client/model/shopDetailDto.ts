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

export interface ShopDetailDto { 
    addressSupplement?: string;
    city?: string;
    contactTypes?: Array<ShopDetailDto.ContactTypesEnum>;
    email?: string;
    id?: string;
    name?: string;
    ownerName?: string;
    street?: string;
    zipCode?: string;
}
export namespace ShopDetailDto {
    export type ContactTypesEnum = 'FACEBOOK_MESSENGER' | 'GLIDE' | 'GOOGLE_DUO' | 'WHATSAPP' | 'SKYPE' | 'JUSTALK' | 'SIGNAL_PRIVATE_MESSENGER' | 'SNAPCHAT' | 'TANGO' | 'VIBER' | 'TELEPHONE';
    export const ContactTypesEnum = {
        FACEBOOKMESSENGER: 'FACEBOOK_MESSENGER' as ContactTypesEnum,
        GLIDE: 'GLIDE' as ContactTypesEnum,
        GOOGLEDUO: 'GOOGLE_DUO' as ContactTypesEnum,
        WHATSAPP: 'WHATSAPP' as ContactTypesEnum,
        SKYPE: 'SKYPE' as ContactTypesEnum,
        JUSTALK: 'JUSTALK' as ContactTypesEnum,
        SIGNALPRIVATEMESSENGER: 'SIGNAL_PRIVATE_MESSENGER' as ContactTypesEnum,
        SNAPCHAT: 'SNAPCHAT' as ContactTypesEnum,
        TANGO: 'TANGO' as ContactTypesEnum,
        VIBER: 'VIBER' as ContactTypesEnum,
        TELEPHONE: 'TELEPHONE' as ContactTypesEnum
    };
}