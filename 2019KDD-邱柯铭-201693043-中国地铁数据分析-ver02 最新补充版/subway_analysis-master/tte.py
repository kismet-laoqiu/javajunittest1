from matplotlib import pyplot as plt
from wordcloud import WordCloud

string = '姚家站 南关岭站 大连北站站 华北路站 泉水站 华南北站 中华广场站 华南广场站 千山路站 松江路站 东纬路站 春光街站 春柳站 香工街站 沙河口火车站站 中长街站  兴工街站 西安路站 功成街站 富国街站 会展中心站 星海广场站 医大二院站 星海公园站 黑石礁站 学苑广场站 海事大学站 凌水站  高新园区站 七贤岭站 河口站 东海公园站 海之韵站  东海新区站 东港站 东港广场站 会议中心站 港湾广场站 中山广场站  胜利广场站 友好广场站 友好街站 青泥洼桥站  一二九街站 长春路站 人民广场站 联合路站 交通大学站 西南路站 师范大学站 辽师站 马栏广场站 马栏广场站 湾家站 红旗西路站 南松路站 虹锦路站 南林路站 虹港路站 机场站 辛寨子站 前革站 中革站 革镇堡站 　　后革站 卫生中心站'
font = r'C:\Windows\Fonts\FZSTK.TTF'
wc = WordCloud(font_path=font,  # 如果是中文必须要添加这个，否则会显示成框框
               background_color='white',
               width=1000,
               height=800,
               ).generate(string)
wc.to_file('ss.png')  # 保存图片
plt.imshow(wc)  # 用plt显示图片
plt.axis('off')  # 不显示坐标轴
plt.show()  # 显示图片

