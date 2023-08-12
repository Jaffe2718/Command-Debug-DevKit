# socket_inject_client.py
import socket as sk
import tkinter as tk
import threading as td

class App(tk.Tk):

    listen_thread: td.Thread
    sock: sk.socket

    def __init__(self):
        super().__init__()
        self.title('Socket')
        self.geometry('300x200')
        self.resizable(False, False)
        # input host and port
        self.entry_host_port = tk.Entry(self)
        self.entry_host_port.place(x=10, y=10, width=180, height=20)
        # button connect
        self.button_connect = tk.Button(self, text='Connect', command=self.connect)
        self.button_connect.place(x=200, y=10, width=60, height=20)
        # text box
        self.text_box = tk.Text(self)
        self.text_box.place(x=10, y=40, width=280, height=120)
        self.text_box.config(state=tk.DISABLED)
        # socket input entry
        self.entry_socket = tk.Entry(self)
        self.entry_socket.place(x=10, y=170, width=180, height=20)
        # button send
        self.button_send = tk.Button(self, text='Send', command=self.send_to_server)
        self.button_send.place(x=200, y=170, width=60, height=20)

    def connect(self):
        host, port = self.entry_host_port.get().split(':')
        self.sock = sk.socket(sk.AF_INET, sk.SOCK_STREAM)
        self.sock.connect((host, int(port.replace(' ', ''))))
        self.text_box.config(state=tk.NORMAL)
        self.text_box.insert(tk.END, '[MESSAGE] Connected to server\n')
        self.text_box.config(state=tk.DISABLED)
        self.listen_thread = td.Thread(target=self.receive)
        self.listen_thread.start()

    def send_to_server(self):
        data = self.entry_socket.get() + '\n'
        self.sock.send(data.encode())
        self.entry_socket.delete(0, tk.END)
        self.text_box.config(state=tk.NORMAL)
        self.text_box.insert(tk.END, data + '\n')
        self.text_box.config(state=tk.DISABLED)
        self.text_box.see(tk.END)

    def receive(self):
        while True:
            data = self.sock.recv(1024).decode('utf-8')
            self.text_box.config(state=tk.NORMAL)
            self.text_box.insert(tk.END, data + '\n')
            self.text_box.config(state=tk.DISABLED)
            self.text_box.see(tk.END)

if __name__ == '__main__':
    app = App()
    app.mainloop()
    app.sock.shutdown(sk.SHUT_RDWR)
    app.sock.close()