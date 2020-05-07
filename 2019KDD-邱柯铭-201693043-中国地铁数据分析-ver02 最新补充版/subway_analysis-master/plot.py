import matplotlib
import matplotlib.pyplot as plt
import xlrd

file = u'./data.xlsx'
xlrd.open_workbook(file)
data = xlrd.open_workbook(file)
table = data.sheet_by_name(u'data')  # 获得表格
x = table.col_values(0)
x.pop(0)
y1=table.col_values(1)
y1.pop(0)
y2=table.col_values(2)
y2.pop(0)
y3=table.col_values(3)
y3.pop(0)
y4=table.col_values(4)
y4.pop(0)





plt.rcParams['font.sans-serif'] = ['SimHei']
matplotlib.rcParams['font.sans-serif'] = ['SimHei']
matplotlib.rcParams['axes.unicode_minus'] = False

plt.figure(12)
plt.subplot(221)
plt.xlabel('地铁站数量')
plt.ylabel('值')
plt.plot(range(len(y1)), y2, 'bo-',label="gdp/亿元")
plt.xticks(range(len(y1)), y1, rotation=45)
plt.grid(True)
plt.legend(bbox_to_anchor=(1.0, 1), loc=1, borderaxespad=0.)

plt.subplot(222)
plt.xlabel('地铁站数量')
plt.ylabel('值')
plt.plot(range(len(y1)), y3, 'go-',label="增长/百分比")
plt.xticks(range(len(y1)), y1, rotation=45)
plt.grid(True)
plt.legend(bbox_to_anchor=(1.0, 1), loc=1, borderaxespad=0.)

plt.subplot(212)
plt.xlabel('地铁站数量')
plt.ylabel('值')
plt.plot(range(len(y1)), y4, 'yo-',label="人口/万人")
plt.xticks(range(len(y1)), y1, rotation=45)
plt.grid(True)
plt.legend(bbox_to_anchor=(1.0, 1), loc=1, borderaxespad=0.)

plt.show()