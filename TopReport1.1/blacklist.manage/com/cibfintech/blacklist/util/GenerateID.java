package com.cibfintech.blacklist.util;

import java.net.InetAddress;

/**
 * �ڷֲ�ʽϵͳ�У���Ҫ���ȫ��UID�ĳ��ϻ��ǱȽ϶�ģ�twitter��snowflake�������������
 * ʵ��Ҳ���Ǻܼ򵥵ģ���ȥ������Ϣ�����Ĵ�����Ǻ��뼶ʱ��41λ+����ID 10λ+����������12λ��
 * ����Ŀ��ַΪ��https://github.com/twitter/snowflake����Scalaʵ�ֵġ�
 * python�����Դ��Ŀhttps://github.com/erans/pysnowflake��
 * 
 * @author xiqiao
 * @Date 2013-12-19
 */
public class GenerateID {
	// ��ݾ�����������ṩ
	private final long workerId;
	// �˲���,ʹʱ���С,��ɵ���λ���С,һ��ȷ�����ܱ䶯
	private final static long twepoch = 1361753741828L;
	private long sequence = 0L;
	private final static long workerIdBits = 10L;
	private final static long maxWorkerId = -1L ^ -1L << workerIdBits;
	private final static long sequenceBits = 12L;

	private final static long workerIdShift = sequenceBits;
	private final static long timestampLeftShift = sequenceBits + workerIdBits;
	private final static long sequenceMask = -1L ^ -1L << sequenceBits;

	private long lastTimestamp = -1L;
	// �������id��ȡ������
	private static GenerateID worker = new GenerateID();

	/**
	 * ���� IdWorker����.
	 * 
	 * @Deprecated ����þ�̬����getId()
	 * @param workerId
	 */
	@Deprecated
	public GenerateID(final long workerId) {
		if (workerId > GenerateID.maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", GenerateID.maxWorkerId));
		}
		this.workerId = workerId;
	}

	public GenerateID() {
		this.workerId = getAddress() % (GenerateID.maxWorkerId + 1);
	}

	public static long getId() {
		return worker.nextId();
	}

	public synchronized long nextId() {
		long timestamp = this.timeGen();
		if (this.lastTimestamp == timestamp) {
			this.sequence = (this.sequence + 1) & GenerateID.sequenceMask;
			if (this.sequence == 0) {
				timestamp = this.tilNextMillis(this.lastTimestamp);
			}
		} else {
			this.sequence = 0;
		}
		if (timestamp < this.lastTimestamp) {
			try {
				throw new Exception(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.lastTimestamp = timestamp;
		long nextId = ((timestamp - twepoch << timestampLeftShift)) | (this.workerId << GenerateID.workerIdShift) | (this.sequence);
		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp1) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp1) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private static long getAddress() {
		try {
			String currentIpAddress = InetAddress.getLocalHost().getHostAddress();
			String[] str = currentIpAddress.split("\\.");
			StringBuilder hardware = new StringBuilder();
			for (int i = 0; i < str.length; i++) {
				hardware.append(str[i]);
			}
			return Long.parseLong(hardware.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 2L;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	public static void main(final String[] args) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			System.out.println(getId());
		}
		long end = System.currentTimeMillis();
		System.out.println((100000 / (end - start)) + "��/ms");
		System.out.println(getId());
	}
}