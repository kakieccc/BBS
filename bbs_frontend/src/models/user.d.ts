export type UserType = {
  id: number;
  userName: string;
  userAccount: string;
  avatarUrl?: string;
  profile?: string;
  gender: number;
  phone: string;
  email: string;
  userState: number;
  userRole: number;
  tags: string[];
  createtime: Date;
};
