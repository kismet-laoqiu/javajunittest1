import xlrd
from xlwt import Workbook

file = u'./subway - 副本.xls'
xlrd.open_workbook(file)
data = xlrd.open_workbook(file)
table = data.sheet_by_name(u'subway - 副本')  # 获得表格
nrows = table.nrows  # 拿到总共行数
colnames = table.row_values(0)  # 某一行数据 ['姓名', '用户名', '联系方式', '密码']
list = []
for rownum in range(1, nrows):  # 也就是从Excel第二行开始，第一行表头不算
    row = table.row_values(rownum)
    if row:
        app = {}
        for i in range(len(colnames)):
            app[colnames[i]] = row[i]  # 表头与数据对应
        list.append(app)
city = {}
for row in list:
    if row['城市'] not in city.keys():
        city[row['城市']]=set()
for row in list:
    city[row['城市']].add(row['地铁站'])
data={}
for k in city:
    data[k]=len(city[k])
print(data)

file = Workbook(encoding='utf-8')
table = file.add_sheet('data')
i=0
for d in data:
    table.write(i, 0, d)
    table.write(i, 1, data[d])
    i+=1
file.save('data.xlsx')