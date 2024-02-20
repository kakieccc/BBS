import {UserType} from "./user";

/**
 * 分区类别
 */
export type BarType = {
    id: number;
    name: string;
    description: string;
    password?: string,
    hasJoin: 
    // todo 定义枚举值类型，更规范
    status: number;
    createTime: Date;
    updateTime: Date;
    createUser?: UserType;
};