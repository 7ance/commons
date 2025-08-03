package cn.lance.commons.util.uuid;


/**
 * Snowflake ID (64 bits)
 * <p>
 * 0 - 00000010001010000011001001011001110111000 - 00001 - 00001 - 000000000111
 * ┬   ──────────────────┬────────────────────——   ──┬──   ──┬──   ─┬──────────
 * │                     │                           │       │      │
 * │                     │                           │       │      └── 12 bits:    sequence (每毫秒的自增序列)
 * │                     │                           │       └───────── 5 bits:     worker ID
 * │                     │                           └───────────────── 5 bits:     datacenter ID
 * │                     └───────────────────────────────────────────── 41 bits:    timestamp offset（相对 epoch 偏移量）
 * └─────────────────────────────────────────────────────────────────── 1 bit:      固定为 0，符号位，始终为正
 */
@SuppressWarnings("FieldCanBeLocal")
public class SnowflakeIdUtils {
    private final long datacenterIdBits = 5L;
    private final long workerIdBits = 5L;
    private final long sequenceBits = 12L;

    /**
     * 起始时间戳：2025-01-01T00:00:00+08:00
     */
    private final long epoch = 1735660800000L;

    /**
     * datacenter ID 位移值
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     * worker ID 位移值
     */
    private final long workerIdShift = sequenceBits;
    /**
     * datacenter ID 位移值
     */
    private final long timestampShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = ~(-1L << sequenceBits);

    private final long datacenterId;
    private final long workerId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdUtils(long workerId, long datacenterId) {
        long maxDatacenterId = ~(-1L << datacenterIdBits);
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID must be between 0 and " + maxDatacenterId);
        }
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("Worker ID must be between 0 and " + maxWorkerId);
        }
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    /**
     * 获取 ID
     *
     * @return id
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards. Refusing to generate ID.");
        }
        if (timestamp - epoch > (1L << 42) - 1) {
            throw new IllegalStateException("Timestamp out of range.");
        }

        if (lastTimestamp == timestamp) {
            // 与上一ID处于同一毫秒
            sequence = (sequence + 1) & sequenceMask;

            // 当前毫秒内4096个ID已用尽
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 新一毫秒
            sequence = 0L;
        }

        // 保存当前毫秒
        lastTimestamp = timestamp;

        return ((timestamp - epoch) << timestampShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}