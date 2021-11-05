const getYears = () => {
  const date = new Date();
  const nowYear = date.getFullYear();
  const years = new Array(nowYear - 2010 + 1).fill('').map((item, index) => index + 2010);
  return years;
};

const getNowYear = () => {
  const date = new Date();
  const nowYear = date.getFullYear();
  return nowYear;
};
const AREA_MAY = [
  { label: '本中心', value: 1 },
  { label: '上海市', value: 2 },
];
const REPORT_TYPE_LIST = [
  { label: '总治疗情况统计表', value: 'TotalInfo' },
  { label: '夫精IVF统计表', value: 'FuJingIvf' },
  { label: '夫精ICSI统计表', value: 'FuJingIcsi' },
  { label: '夫精IVF+ICSI统计表', value: 'FuJingIvfIcsi' },
  { label: '供精IVF（ICSI）统计表', value: 'GongJingIvf' },
  { label: 'FET统计表', value: 'Fet' },
  { label: 'PGT-A统计表', value: 'Pgta' },
  { label: 'PGT-SR统计表', value: 'Pgtsr' },
  { label: 'PGT-M统计表', value: 'Pgtm' },
  { label: 'AIH统计表', value: 'Aih' },
  { label: 'AID统计表', value: 'Aid' },
  { label: '赠、受卵统计表', value: 'ZengShouLuan' },
  { label: '冻卵融卵统计表', value: 'DongRongLuan' },
];
const REPORT_TYPE_MAP = {
  TotalInfo: '总治疗情况统计表',
  FuJingIvf: '夫精IVF统计表',
  FuJingIcsi: '夫精ICSI统计表',
  FuJingIvfIcsi: '夫精IVF+ICSI统计表',
  GongJingIvf: '供精IVF（ICSI）统计表',
  Fet: 'FET统计表',
  Pgta: 'PGT-A统计表',
  Pgtsr: 'PGT-SR统计表',
  Pgtm: 'PGT-M统计表',
  Aih: 'AIH统计表',
  Aid: 'AID统计表',
  ZengShouLuan: '赠、受卵统计表',
  DongRongLuan: '冻卵融卵统计表',
};
const AGE_RANGE_MAY = [
  { label: '各年龄段汇总', value: '合计' },
  { label: '<35岁', value: '<35' },
  { label: '35-39岁', value: '35-39' },
  { label: '40-42岁', value: '40-42' },
  { label: '43-44岁', value: '43-44' },
  { label: '45-50岁', value: '45-50' },
];
const AGE_RANGE_MAY_4044 = [
  { label: '各年龄段汇总', value: '合计' },
  { label: '<35岁', value: '<35' },
  { label: '35-39岁', value: '35-39' },
  { label: '40-44岁', value: '40-44' },
  { label: '45-50岁', value: '45-50' },
];
const SITUATION_MAY = [
  { label: '临床妊娠率', value: 1 },
  { label: '多胎妊娠率', value: 2 },
  { label: '宫外孕发生率', value: 3 },
  { label: '流产率', value: 4 },
  { label: '活产率', value: 5 },
  { label: '分娩率', value: 6 },
  { label: '出生缺陷发生率', value: 7 },
];
const USER_STATUS_MAP = {
  0: '管理员',
  1: '未审核',
  2: '审核中',
  3: '使用中',
  4: '已封停',
  5: '已删除',
  6: '不通过',
};
const PROVICE_LIST = [
  '河北省',
  '山西省',
  '吉林省',
  '辽宁省',
  '黑龙江省',
  '陕西省',
  '甘肃省',
  '青海省',
  '山东省',
  '福建省',
  '浙江省',
  '台湾省',
  '河南省',
  '湖北省',
  '湖南省',
  '江西省',
  '江苏省',
  '安徽省',
  '广东省',
  '海南省',
  '四川省',
  '贵州省',
  '云南省',
  '北京市',
  '上海市',
  '天津市',
  '重庆市',
  '内蒙古自治区',
  '新疆维吾尔自治区',
  '宁夏回族自治区',
  '广西壮族自治区',
  '西藏自治区',
  '香港特别行政区',
  '澳门特别行政区',
];

const USER_STATUS_LIST = [
  { label: '所有状态', value: null },
  { label: '未审核', value: 1 },
  { label: '审核中', value: 2 },
  { label: '使用中', value: 3 },
  { label: '已封停', value: 4 },
  { label: '不通过', value: 6 },
];

export {
  getYears,
  getNowYear,
  AREA_MAY,
  REPORT_TYPE_MAP,
  AGE_RANGE_MAY,
  AGE_RANGE_MAY_4044,
  SITUATION_MAY,
  REPORT_TYPE_LIST,
  USER_STATUS_MAP,
  PROVICE_LIST,
  USER_STATUS_LIST,
};
