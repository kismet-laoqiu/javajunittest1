# https://www.shiyanlou.com/

import sys, time, io
from lib.core.Spider import SpiderMain
from script.UI import Ui_MainWindow
from PyQt5.QtWidgets import QMainWindow, QApplication, QFileDialog, QMessageBox


class SQL_detection_Window(QMainWindow, Ui_MainWindow):
    def __init__(self, parent=None):
        super(SQL_detection_Window, self).__init__(parent)
        self.setupUi(self)
        self.state = 0  # 0:未开始 1：运行中 2：暂停中
        self.spider = None
        self.root = ""
        self.log_str = ""
        self.url_num = 0
        self.sql_num = 0
        self.state_button.clicked.connect(self.state_button_clicked)
        self.end_button.clicked.connect(self.end_button_clicked)
        self.toolButton.clicked.connect(self.toolButton_clicked)
        self.end_button.setEnabled(False)
        self.toolButton.setEnabled(True)

    def state_button_clicked(self):
        if self.state == 0:
            self.log.setText("")
            self.state = 1
            self.state_button.setText("暂停")
            self.toolButton.setEnabled(False)
            self.end_button.setEnabled(True)
            self.root = self.url_text.text()
            self.spider = SpiderMain(self.root)
            self.spider.start()
            self.spider.spider_finished.connect(self.end_spider)
            self.spider.url_num.connect(self.url_num_update)
            self.spider.sql_num.connect(self.sql_num_update)
        elif self.state == 1:
            self.state = 2
            self.toolButton.setEnabled(True)
            self.state_button.setText("继续")
            self.spider.pause()
        else:
            self.state = 1
            self.state_button.setText("暂停")
            self.toolButton.setEnabled(False)
            self.spider.resume()

    def end_button_clicked(self):
        self.spider.end()
        self.state = 0
        self.state_button.setText("开始")
        time.sleep(0.08)  # 防止按钮变色异常
        self.toolButton.setEnabled(True)
        self.end_button.setEnabled(False)

    def toolButton_clicked(self):
        dir = QFileDialog.getExistingDirectory(self.centralwidget, "选取文件夹", "./")
        path = dir + r"/sql_detection_log.txt"
        with open(path, "w+") as f:
            f.write(self.log_str)
        QMessageBox.information(self, "Information",
                                self.tr("导出成功!"))

    def end_spider(self):
        self.state = 0
        self.state_button.setText("开始")
        self.toolButton.setEnabled(True)
        self.end_button.setEnabled(False)

    def url_num_update(self, s):
        self.url_num += 1
        self.log_str += s
        self.url_num_text.setText(str(self.url_num))
        self.log.append(s)

    def sql_num_update(self, s):
        self.sql_num += 1
        self.log_str += s
        self.sql_num_text.setText(str(self.sql_num))
        self.log.append(s)


def main():
    app = QApplication(sys.argv)
    window = SQL_detection_Window()
    window.show()
    sys.exit(app.exec_())


if __name__ == '__main__':
    main()
