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
import {DayDto} from './dayDto';

export interface SlotConfigDto {
    friday?: DayDto;
    monday?: DayDto;
    saturday?: DayDto;
    sunday?: DayDto;
    thursday?: DayDto;
    timeBetweenSlots?: number;
    timePerSlot?: number;
    tuesday?: DayDto;
    wednesday?: DayDto;
}
