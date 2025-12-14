export interface SectorTreeDto {
    id: number;
    name: string;
    path: string;
    children: SectorTreeDto[];
}

export interface UserSectorSaveRequest {
    username: string;
    agreeToTerms: boolean;
    sectorIds: number[];
}

export interface UserSectorResponse {
    userId: number;
    username: string;
    agreeToTerms: boolean;
    sectorIds: number[];
}
