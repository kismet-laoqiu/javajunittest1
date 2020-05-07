from lib.core import Download, UrlManager
from urllib.parse import urljoin
from bs4 import BeautifulSoup
from script import sqlcheck
from PyQt5.QtCore import *
import threading


class SpiderMain(QThread):
    spider_finished = pyqtSignal()
    url_num = pyqtSignal(str)
    sql_num = pyqtSignal(str)

    def __init__(self, root, parent=None):
        super(SpiderMain, self).__init__(parent)
        self.__flag = threading.Event()  # 用于暂停线程的标识
        self.__flag.set()  # 设置为True
        self.__running = threading.Event()  # 用于停止线程的标识
        self.__running.set()  # 将running设置为True
        self.urls = UrlManager.UrlManager()
        self.download = Download.Downloader()
        self.root = root

    def pause(self):
        self.__flag.clear()  # 设置为False, 让线程阻塞

    def resume(self):
        self.__flag.set()  # 设置为True, 让线程停止阻塞

    def end(self):
        self.__flag.set()  # 将线程从暂停状态恢复, 如何已经暂停的话
        self.__running.clear()  # 设置为False

    def _judge(self, domain, url):
        if (url.find(domain) != -1):
            return True
        else:
            return False

    def _parse(self, page_url, content):
        if content is None:
            return
        soup = BeautifulSoup(content, 'html.parser')
        _news = self._get_new_urls(page_url, soup)
        return _news

    def _get_new_urls(self, page_url, soup):
        new_urls = set()
        links = soup.find_all('a')
        for link in links:
            new_url = link.get('href')
            new_full_url = urljoin(page_url, new_url)
            if (self._judge(self.root, new_full_url)):
                new_urls.add(new_full_url)
        return new_urls

    def run(self):
        self.urls.add_new_url(self.root)
        while self.urls.has_new_url() and self.__running.isSet():
            _content = []
            th = []
            if self.urls.has_new_url() is False:
                break
            new_url = self.urls.get_new_url()
            s = "\ncraw:" + new_url
            self.url_num.emit(s)
            ##sql check
            try:
                if sqlcheck.sqlcheck(new_url):
                    s = "url:%s sqlcheck is valueable" % new_url
                    self.sql_num.emit(s)
            except:
                pass
            self.download.download(new_url, _content)
            for _str in _content:
                if _str is None:
                    continue
                new_urls = self._parse(new_url, _str["html"])
                self.urls.add_new_urls(new_urls)
            self.__flag.wait()
        self.spider_finished.emit()
